using System.Runtime.InteropServices;

namespace Stargazer.AppHost;

public static class MySqlResourceExtension
{
    private static IResourceBuilder<MySqlServerResource> MySql { get; set; }

    public static IResourceBuilder<MySqlDatabaseResource> CreateMySqlDatabaseResource(
        this IDistributedApplicationBuilder builder)
    {
        string tag = "9.5";
        Architecture architecture = RuntimeInformation.ProcessArchitecture;
        if (architecture == Architecture.Arm
            || architecture == Architecture.Arm64)
        {
            tag =  "9.5-arm64";
        }
        var password = builder.AddParameter("password", "123456");
        MySql = builder.AddMySql("MySql", password)
            .WithContainerName("mysql")
            .WithImageRegistry("ccr.ccs.tencentyun.com")
            .WithImage("stargazer/mysql",tag)
            .WithDataBindMount("../../volumes/mysql/data");
        return MySql.AddDatabase("mysql-database");
    }

    public static IResourceBuilder<MySqlServerResource> GetMySqlServerResource(this IResourceBuilder<MySqlDatabaseResource> builder)
    {
        return MySql;
    }
}