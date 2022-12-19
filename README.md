# oauth2-demo


oauth2-demo
~~~
├── oauth2-authorization-code                      // 授权码模式
│       └── authorization-code-authorization-server           // 认证服务器
│       └── authorization-code-resource-server                // 资源服务器
├───pom.xml                                        // 公共依赖
├── oauth2-client-credentials                      // 客户端凭证模式
│       └── client-credentials-authorization-server           // 认证服务器
│       └── client-credentials-resource-server                // 资源服务器
├───pom.xml                                        // 公共依赖
├── oauth2-implicit                                // 简化模式
│       └── implicit-authorization-server                     // 认证服务器
│       └── implicit-resource-server                          // 资源服务器
├───pom.xml                                        // 公共依赖
├── oauth2-password                                // 密码模式
│       └── password-authorization-server                     // 认证服务器
│       └── password-resource-server                          // 资源服务器
├───pom.xml                                        // 公共依赖
~~~

## 1. 授权码模式

### (1) 访问http://localhost:9091/permit_demo
返回：
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
未授权，无法调通接口

### (2) 获取授权链接
调用接口：
http://127.0.0.1:9091/oauth2_token/get_authorization_url

返回授权链接：
http://127.0.0.1:9090/oauth/authorize?client_id=clientapp&redirect_uri=http://127.0.0.1:9091/oauth2_token/callback&scope=read_userinfo&response_type=code

### (3) 访问授权链接

### (4) 输入账号密码，test/123456，点击登录，并同意授权

### (5) OAuth2会自动重定向到redirect_uri（自定义地址）

### (6) 授权回调接口（/oauth2_token/callback）里面再调用获取token接口，获取token信息

### (7) 再次访问http://localhost:9091/permit_demo
并添加header，{"Authorization" : "Bearer access_token"}
例如：{"Authorization" : "Bearer 98a2d932-3bea-4c11-924a-a0bfe16c17ed"}
即可调通接口


## 2. 客户端凭证模式

### (1) 访问http://localhost:9091/client_login/token
即可拿到token

### (2) 再次访问http://localhost:9091/permit_demo
并添加header，{"Authorization" : "Bearer access_token"}
例如：{"Authorization" : "Bearer 98a2d932-3bea-4c11-924a-a0bfe16c17ed"}
即可调通接口


## 3. 简化模式

### (1) 访问http://localhost:9091/login/get_authorization_url
获取授权链接

### (2) 访问授权链接
例如：
http://127.0.0.1:9090/oauth/authorize?client_id=clientapp&redirect_uri=http://127.0.0.1:9091/login/callback&scope=read_userinfo&response_type=token&state=1671368953332

### (3) 输入账号密码，test/123456，点击登录，并同意授权

### (4) OAuth2会自动重定向到redirect_uri（自定义地址）
例如：
http://127.0.0.1:9091/login/callback#access_token=a937f680-6c62-49c7-afb0-9c1df26c86be&token_type=bearer&state=1671368953332&expires_in=299

在前端网址即可拿到token，这种方式把令牌直接传给前端，是很不安全的。


## 4. 密码模式

### (1) 访问http://localhost:9091/login/token?username=test&password=123456
即可拿到token

### (2) 再次访问http://localhost:9091/permit_demo
并添加header，{"Authorization" : "Bearer access_token"}
例如：{"Authorization" : "Bearer 98a2d932-3bea-4c11-924a-a0bfe16c17ed"}
即可调通接口

