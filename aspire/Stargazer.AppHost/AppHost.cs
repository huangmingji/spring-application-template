using Stargazer.AppHost;

var builder = DistributedApplication.CreateBuilder(args);

// var redis = builder.CreateRedisResource();
// var mysql = builder.CreateMySqlDatabaseResource();

var javaApp = builder.AddSpringApp(
    "java-app",
    "../../",
    new JavaAppExecutableResourceOptions()
    {
        OtelAgentPath = "../../agents/opentelemetry-javaagent.jar"
    })
    .WithExternalHttpEndpoints();
    // .WithReference(redis)
    // .WaitFor(redis)
    // .WithReference(mysql)
    // .WaitFor(mysql.GetMySqlServerResource());
    
builder.Build().Run();
