# 不同Listener的执行顺序

1. 应用启动，自动触发ServletContextListener.contextInitialized()和Filter.init()监听器的初始化方法。
    
2. 有请求到服务器，触发ServletRequestListener.requestInitialized()。
    
3. 浏览器和服务器创建session时,触发HttpSessionListener.sessionCreated() 生命时间：1800 s。
   > Tip：进入servlet中的映射方法后，controller方法中创建session触发session监听器。[HttpSession session = request.getSession(true);]