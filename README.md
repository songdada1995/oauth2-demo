# oauth2-demo


oauth2-demo
~~~
├── oauth2-authorization-code             // 授权码模式
│       └── authorization-server                     // 认证服务器
│       └── resource-server                          // 资源服务器
├───pom.xml                               // 公共依赖
├── oauth2-client-credentials             // 客户端凭证模式
│       └── authorization-server                     // 认证服务器
├───pom.xml                               // 公共依赖
~~~

## 授权码模式

### 1. 访问http://localhost:9091/permit_demo
返回：
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
未授权，无法调通接口

### 2. 获取授权链接
调用接口：
http://127.0.0.1:9091/oauth2_token/get_authorization_url

返回授权链接：
http://127.0.0.1:9090/oauth/authorize?client_id=clientapp&redirect_uri=http://127.0.0.1:9091/oauth2_token/callback&scope=read_userinfo&response_type=code

### 3. 访问授权链接

### 4. 输入账号密码，test/123456，点击登录，并同意授权

### 5. OAuth2会自动重定向到redirect_uri（自定义地址）

### 6. 授权回调接口（/oauth2_token/callback）里面再调用获取token接口，获取token信息

### 7. 再次访问http://localhost:9091/permit_demo
并添加header，{"Authorization" : "Bearer access_token"}
例如：{"Authorization" : "Bearer 98a2d932-3bea-4c11-924a-a0bfe16c17ed"}
即可调通接口
