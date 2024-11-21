import { createRouter, createWebHistory } from 'vue-router'
import QuizSolveView from '@/views/QuizSolveView.vue'
import JoinView from '@/views/JoinView.vue'
import { getIsLoggedIn } from '@/api/userApi.js'
import { useUser } from '@/stores/user.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: QuizSolveView,
    },
    {
      path: '/join',
      name: 'join',
      component: JoinView,
    }
  ],
})

router.beforeEach(async () => {
  const user = useUser()
  try {
    await getIsLoggedIn()
    user.login()
  } catch {
    user.logout()
  }
})

export default router
