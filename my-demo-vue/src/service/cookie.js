import Cookies from 'js-cookie';

export const cookieUtil = {
    // 保存 token
    setToken(token, rememberMe = false) {
        const options = {
            expires: rememberMe ? 7 : 1, // 天数
            path: '/',
            secure: process.env.NODE_ENV === 'production', // 生产环境启用 HTTPS
            sameSite: 'strict'
        };
        
        Cookies.set('jwt_token', token, options);
    },
    
    // 获取 token
    getToken() {
        return Cookies.get('jwt_token');
    },
    
    // 删除 token
    removeToken() {
        Cookies.remove('jwt_token', { path: '/' });
    },
    
    // // 保存用户信息
    // setUser(user, rememberMe = false) {
    //     const options = {
    //         expires: rememberMe ? 7 : 1,
    //         path: '/',
    //         secure: process.env.NODE_ENV === 'production',
    //         sameSite: 'strict'
    //     };
        
    //     Cookies.set('user_info', JSON.stringify(user), options);
    // },
    
    // // 获取用户信息
    // getUser() {
    //     const userStr = Cookies.get('user_info');
    //     return userStr ? JSON.parse(userStr) : null;
    // },
    
    // // 删除用户信息
    // removeUser() {
    //     Cookies.remove('user_info', { path: '/' });
    // }
};