一.简介
(一).客户端的授权模式
    1.授权码模式(authorizationcode)
    (A)用户访问客户端,后者将前者导向认证服务器。
    	1).入参:
    		esponse_type：	表示授权类型,必选项,此处的值固定为"code"
    		client_id：		表示客户端的ID,必选项
    		redirect_uri：	表示重定向URI,可选项
    		scope：			表示申请的权限范围,可选项
    		state：			表示客户端的当前状态,可以指定任意值,认证服务器会原封不动地返回这个值。
    	2).示例:
    		GET/authorize?response_type=code&client_id=s6BhdRkqt3&state=xyz&redirect_uri=https://client.example.com/cbHTTP/1.1
    		Host:server.example.com

    (B)用户选择是否给予客户端授权。

    (C)假设用户给予授权,认证服务器将用户导向客户端事先指定的"重定向URI"(redirectionURI),同时附上一个授权码。
    	1).重定向:
    		code：			表示授权码,必选项。短暂有效:10分钟,仅可使用一次,该码与客户端ID和重定向URI,是一一对应关系。
    		state：			如果客户端的请求中包含这个参数,认证服务器的回应也必须一模一样包含这个参数。
    	2).示例:
    		HTTP/1.1302Found
    		Location:https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&state=xyz

    (D)客户端收到授权码,附上早先的"重定向URI",向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的,对用户不可见。
    	1).入参:
    		grant_type：	表示使用的授权模式,必选项,此处的值固定为"authorization_code"。
    		code：			表示上一步获得的授权码,必选项。
    		redirect_uri：	表示重定向URI,必选项,且必须与A步骤中的该参数值保持一致。
    		client_id：		表示客户端ID,必选项
    	2).示例:
    		POST/tokenHTTP/1.1
    		Host:server.example.com
    		Authorization:BasicczZCaGRSa3F0MzpnWDFmQmF0M2JW
    		Content-Type:application/x-www-form-urlencoded
    		grant_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA
    		&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb

    (E)认证服务器核对了授权码和重定向URI,确认无误后,向客户端发送访问令牌(accesstoken)和更新令牌(refreshtoken)
    	1).响应:
    		access_token：	表示访问令牌,必选项。
    		token_type：	表示令牌类型,该值大小写不敏感,必选项,可以是bearer类型或mac类型。
    		expires_in：	表示过期时间,单位为秒。如果省略该参数,必须其他方式设置过期时间。
    		refresh_token：	表示更新令牌,用来获取下一次的访问令牌,可选项。
    		scope：			表示权限范围,如果与客户端申请的范围一致,此项可省略。
    	2).示例:
    		HTTP/1.1200 OK
    		Content-Type:application/json;charset=UTF-8
    		Cache-Control:no-store
    		Pragma:no-cache
    		{
    		"access_token":"2YotnFZFEjr1zCsicMWpAA",
    		"token_type":"example",
    		"expires_in":3600,
    		"refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
    		"example_parameter":"example_value"
    		}

    2.简化模式(implicit)
    3.密码模式(resourceownerpasswordcredentials)
    4.客户端模式(clientcredentials)

    二.OAUTH官网示例:
    client_id	C0_IJ26R6PAr0qd6Ayt9VcPi
    client_secret	yeGR4ef-MeXOFViEjm-gvcZ2LrAJGeEpx26qWk0KuJZ9Ki1P
    登录helpless-hawk@example.com
    密码Rich-Elephant-65

    1.构建授权URL
    https://authorization-server.com/authorize?response_type=code&client_id=C0_IJ26R6PAr0qd6Ayt9VcPi&redirect_uri=https://www.oauth.com/playground/authorization-code.html&scope=photo+offline_access&state=GVBJ6FG9CTfFgc6t

    2.验证参数状态
    重定向:https://www.oauth.com/playground/authorization-code.html?state=GVBJ6FG9CTfFgc6t&code=ZZ0_0PPEu69LY7r1B660ns13mzrw6zEuxd4QxwSBuZrZIyfQ

    3.交换授权码,获取令牌
    POST https://authorization-server.com/token

    grant_type=authorization_code&client_id=C0_IJ26R6PAr0qd6Ayt9VcPi
    &client_secret=yeGR4ef-MeXOFViEjm-gvcZ2LrAJGeEpx26qWk0KuJZ9Ki1P
    &redirect_uri=https：//www.oauth.com/playground/authorization-code.html
    &code=ZZ0_0PPEu69LY7r1B660ns13mzrw6zEuxd4QxwSBuZrZIyfQ

    4.令牌端点响应
    {
      "token_type"："承载者",
      "expires_in"：86400,
      "access_token"："W6fjYMd_Mw9odg62hdDIAkngEL5ohQpsBx3xvtq5uJ_PHp6idBt-Y3gqtoEg6BfzRLsi67f_",
      "scope"："照片offline_access",
      "refresh_token"："DQo6YV_Bo-n7-TnzFdjHePh0"
    }

三.本例代码示例:
    1.创建client/server库,server库执行init.sql
    2.操作示例:
        base64(client-id:client-secret)---->dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==

        server:
        POST http://localhost:8080/oauth/token?grant_type=password&username=admin&password=123456&scope=all
        Accept: */*
        Cache-Control: no-cache
        Authorization: Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==
        注: Authorization: Basic+空格base64(client-id:client-secret)

        token:
        eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTY4MjIxMjIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiMDM5MTFiZDMtNTJmYS00ZmYyLThhYzctYzQwYjlmOTI0MTE0IiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.FglUGR_rLaX-fixjBUNC0IClR3__AfTuG9qCxjgiHDw

        {
        "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTY4MjIxMjIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiMDM5MTFiZDMtNTJmYS00ZmYyLThhYzctYzQwYjlmOTI0MTE0IiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.FglUGR_rLaX-fixjBUNC0IClR3__AfTuG9qCxjgiHDw",
        "token_type":"bearer",
        "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiIwMzkxMWJkMy01MmZhLTRmZjItOGFjNy1jNDBiOWY5MjQxMTQiLCJleHAiOjE1OTY4NTQ1MjIsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sImp0aSI6IjMzYTA0OWZlLTZjY2ItNGQyNS1iN2ViLTQ5YTQ5ODZlYTY5MSIsImNsaWVudF9pZCI6InVzZXItY2xpZW50In0.8SZTtsu1Mo0jOvGVSbuDOgBDMJluF5ZLpc1Gh36S-_I",
        "expires_in":3598,
        "scope":"all",
        "jti":"03911bd3-52fa-4ff2-8ac7-c40b9f924114"
        }

        请求资源:
        GET http://localhost:9000/client-user/get
        Accept: */*
        Cache-Control: no-cache #不允许缓存
        Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTY4MjAxMDIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXSwianRpIjoiYWY1ZDNiYTQtOThhMC00YTExLWExZWItMjUzODZhYzc2ZDZiIiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.7TCHk9Fre9AaozogHLYvPhdGb6nMDi92586zLfsvZAY
        注: Authorization: bearer+空格+token