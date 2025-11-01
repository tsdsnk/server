import { createRouter, createWebHistory } from "vue-router"


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/user",
            component: () => import("../views/User.vue"),
            children: [
                {
                    path: "host",
                    component: () => import("../views/user/Host.vue")
                },
                
            ]
        },
        {
            path: "/admin",
            component: () => import("../views/Admin.vue"),
            children: [
                {
                    path: "host",
                    component: () => import("../views/admin/Host.vue")
                },
                {
                    path: "account",
                    component: () => import("../views/admin/Account.vue")
                },
                {
                    path: "application",
                    component: () => import('../views/admin/Application.vue')
                },
            
            ]
        },
        {
            path: "/login",
            component: () => import("../views/Login.vue")
        },
        {
            path: "/",
            redirect: "/login"
        },
        // {
        //     path: "*",
        //     redirect: "/notfound"
        // }
    ]
});

export default router;