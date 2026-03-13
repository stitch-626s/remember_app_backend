import { createApp } from 'vue'
import App from './App.vue'
import router from './router/route'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './global-style.css'


// const app = createApp(App)
// app.use(router).use(ElementPlus, {
//   locale: zhCn,
// })
// app.mount('#app')

createApp(App).use(router).use(ElementPlus, {
  locale: zhCn,
}).mount('#app')
