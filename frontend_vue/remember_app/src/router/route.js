import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/login'},
        { path: '/login', component: () => import('../views/PageLogin.vue') },
        { path: '/register', component: () => import('../views/PageRegister.vue')},
        { path: '/home', component: () => import('../views/PageHome.vue')}
    ]
})

export default router