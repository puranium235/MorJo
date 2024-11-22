import { createRouter, createWebHistory } from 'vue-router'
import QuizSolveView from '@/views/QuizSolveView.vue'
import JoinView from '@/views/JoinView.vue'
import QuizCreateView from '@/views/QuizCreateView.vue'
import { getIsLoggedIn } from '@/api/userApi.js'
import { useUser } from '@/stores/user.js'
import ErrorView from '@/views/ErrorView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: QuizSolveView,
    },
    {
      path: '/quiz/:quizId',
      name: 'quiz',
      component: QuizSolveView,
    },
    {
      path: '/join',
      name: 'join',
      component: JoinView,
    },
    {
      path: '/create',
      name: 'create',
      component: QuizCreateView,
      beforeEnter: (to, from) => {
        const user = useUser()
        if (!user.isLoggedIn) {
          alert('로그인이 필요합니다.')
          return { name: 'home'}
        }
        return true
      }
    },
    {
      path: '/404',
      name: 'notFound',
      component: ErrorView,
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/404'
    },
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
