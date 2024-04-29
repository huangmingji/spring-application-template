package com.stargazer.springapplicationtemplate.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.stargazer.springapplicationtemplate.domain.User;
import com.stargazer.springapplicationtemplate.repositories.IUserRepository;
import com.stargazer.springapplicationtemplate.services.interfaces.IUserServices;
import com.stargazer.springapplicationtemplate.services.models.users.CreateAccountModel;
import com.stargazer.springapplicationtemplate.services.models.users.CreateOrUpdateUserModel;
import com.stargazer.springapplicationtemplate.services.models.users.UpdateAvatarModel;
import com.stargazer.springapplicationtemplate.services.models.users.UserMapper;
import com.stargazer.springapplicationtemplate.services.models.users.UserModel;
import com.stargazer.springapplicationtemplate.services.models.users.VerifyPasswordModel;
import com.stargazer.springapplicationtemplate.utils.SnowflakeIdGenerator;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataAlreadyExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.DataNotExistsException;
import com.stargazer.springapplicationtemplate.utils.exceptions.ParameterEmptyException;
import com.stargazer.springapplicationtemplate.utils.exceptions.ParameterInvalidException;

@Service
public class UserService implements IUserServices {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel createAccount(CreateAccountModel model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(model.getAccount() == null || model.getAccount().isEmpty())
        {
            throw new ParameterEmptyException("账户名称不能为空");
        }
        if(model.getPassword() == null || model.getPassword().isEmpty())
        {
            throw new ParameterEmptyException("密码不能为空");
        }
        User user = userRepository.findByAccount(model.getAccount());
        if(user != null)
        {
            throw new DataAlreadyExistsException("用户名已存在");
        }

        user = new User();
        user.setId(SnowflakeIdGenerator.getInstance().nextId());
        user.setAccount(model.getAccount());
        user.setPassword(model.getPassword());
        userRepository.insert(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserModel createUser(CreateOrUpdateUserModel model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(model.getAccount() == null || model.getAccount().isEmpty())
        {
            throw new ParameterEmptyException("账户名称不能为空");
        }
        if(model.getPassword() == null || model.getPassword().isEmpty())
        {
            throw new ParameterEmptyException("密码不能为空");
        }
        if(model.getNickName() == null || model.getNickName().isEmpty())
        {
            throw new ParameterEmptyException("昵称不能为空");
        }
        if(model.getEmail() == null || model.getEmail().isEmpty())
        {
            throw new ParameterEmptyException("邮箱不能为空");
        }
        String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!Pattern.compile(EMAIL_PATTERN).matcher(model.getEmail()).matches())
        {
            throw new ParameterInvalidException("邮箱格式错误");
        }
        if(model.getPhoneNumber() == null || model.getPhoneNumber().isEmpty())
        {
            throw new ParameterEmptyException("手机号不能为空");
        }
        User user = userRepository.findByAccount(model.getAccount());
        if(user != null)
        {
            throw new DataAlreadyExistsException("用户名已存在");
        }

        user = new User();
        user.setId(SnowflakeIdGenerator.getInstance().nextId());
        user.setAccount(model.getAccount());
        user.setPassword(model.getPassword());
        user.setNickName(model.getNickName());
        user.setEmail(model.getEmail());
        user.setPhoneNumber(model.getPhoneNumber());
        userRepository.insert(user);
        return UserMapper.toDto(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);        
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserModel findUserById(long id) {
        User user = userRepository.findById(id);
        if(user == null) {
            throw new DataNotExistsException("用户不存在");
        }
        return UserMapper.toDto(user);
    }

    @Override
    public UserModel updateAvatar(UpdateAvatarModel model) {
        User user = userRepository.findById(model.getId());
        if(user == null) {
            throw new DataNotExistsException("用户不存在");
        }
        user.setAvatar(model.getAvatar());
        userRepository.update(user);
        return UserMapper.toDto(user);
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public UserModel updateUser(long id, CreateOrUpdateUserModel model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(model.getAccount() == null || model.getAccount().isEmpty())
        {
            throw new ParameterEmptyException("账户名称不能为空");
        }
        if(model.getPassword() == null || model.getPassword().isEmpty())
        {
            throw new ParameterEmptyException("密码不能为空");
        }
        if(model.getNickName() == null || model.getNickName().isEmpty())
        {
            throw new ParameterEmptyException("昵称不能为空");
        }
        if(model.getEmail() == null || model.getEmail().isEmpty())
        {
            throw new ParameterEmptyException("邮箱不能为空");
        }
        String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!Pattern.compile(EMAIL_PATTERN).matcher(model.getEmail()).matches())
        {
            throw new ParameterInvalidException("邮箱格式错误");
        }
        if(model.getPhoneNumber() == null || model.getPhoneNumber().isEmpty())
        {
            throw new ParameterEmptyException("手机号不能为空");
        }
        User user = userRepository.findById(id);
        if(user == null) {
            throw new DataNotExistsException("用户不存在");
        }
        user.setAccount(model.getAccount());
        user.setPassword(model.password);
        user.setNickName(model.getNickName());
        user.setEmail(model.getEmail());
        user.setPhoneNumber(model.getPhoneNumber());
        userRepository.update(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserModel verifyPassword(VerifyPasswordModel model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = userRepository.findByAccount(model.getAccount());
        if(user == null) {
            throw new DataNotExistsException("账户密码错误");
        }
        user.VerifyPassword(model.getPassword());
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserModel> getUsersByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<User> data = userRepository.findList();
        return UserMapper.toDto(data);
    }
    
}
