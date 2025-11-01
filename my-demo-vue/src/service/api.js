// src/services/api.js
import axios from 'axios';
import { cookieUtil } from './cookie';

export const api = axios.create({
    baseURL: "http://localhost:8088",
    timeout: 10000,
    withCredentials: true, // 重要：允许携带 cookies
});

const WHITE_LIST = [
    '/login',
];
const isWhiteList = (url) => {
    return WHITE_LIST.some(path => url.includes(path));
};

// 请求拦截器
api.interceptors.request.use(
    (config) => {
        if(!isWhiteList(config.url)){
            const token = cookieUtil.getToken();
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            } else{
                window.location.href = '/login'
            }
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
api.interceptors.response.use(
    (response) => {
        if(response.data.code === 401 || response.data.code === 403) {
            // window.location.href = '/login';
        }
        return response.data;
    },
    (error) => {
        if (error.response?.status === 401 || error.response?.status === 403) {
            cookieUtil.removeToken();
            // window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);



