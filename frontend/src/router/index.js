import { createRouter, createWebHistory } from 'vue-router'
import QuizSolveView from '@/views/QuizSolveView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: QuizSolveView,
    }
  ],
})

export default router
