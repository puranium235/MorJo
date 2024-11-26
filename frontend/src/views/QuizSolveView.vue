<template>
  <div v-if="showNext" @click="handleShowNextClick" class="show-next"></div>
  <div v-if="quiz.content" class="container">
    <quiz-content :content="quiz.content"></quiz-content>
    <quiz-option v-for="(option, index) in quiz.options" :key="option" :option="option" :total="result.total"
                 :isResult="isResult" :votes="result.userAnswers[index]" :isAnswer="result.answer - 1 === index"
                 :selected="index === userAnswer - 1" @click="handleAnswerClick(index)"></quiz-option>
    <hr class="line">
    <div class="this-is">이건</div>
    <div class="quiz-buttons">
      <quiz-button value="상식이다" :selected="isCommonSense === true" :total="result.total" :votes="result.isCommonSense"
                   :isResult="isResult" @click="handleButtonClick(true)"></quiz-button>
      <quiz-button value="아니다" :selected="isCommonSense === false" :total="result.total" :votes="result.notCommonSense"
                   :isResult="isResult" @click="handleButtonClick(false)"></quiz-button>
    </div>
  </div>
  <div v-else class="container">
    <div class="no-quiz">퀴즈가 없어요!</div>
    <button class="go-create" @click="router.push({ name: 'create' })">퀴즈 등록하러 가기</button>
  </div>
</template>

<script setup>
import QuizContent from '@/components/quiz/QuizContent.vue'
import QuizOption from '@/components/quiz/QuizOption.vue'
import QuizButton from '@/components/quiz/QuizButton.vue'

import { ref, onMounted, watch } from 'vue'
import { getQuiz, getQuizResult, getRandomQuiz, postQuizSubmit } from '@/api/quizApi.js'
import { useUser } from '@/stores/user.js'
import router from '@/router/index.js'
import { useRoute } from 'vue-router'

const user = useUser()
const route = useRoute()

const quiz = ref({
  quizId: 0,
  content: '지금 내 마음은?',
  options: [
    '안녕하세요',
    '여긴싸피에요',
    '살려주세요',
    '집에가고싶어'
  ]
})
const userAnswer = ref(0)
const isCommonSense = ref(0)

const handleAnswerClick = async (index) => {
  if (isResult.value) {
    return
  }

  userAnswer.value = index + 1
  await submit()
}

const isResult = ref(false)
const result = ref({
  answer: 0,
  userAnswers: [],
  isCommonSense: 0,
  notCommonSense: 0,
  total: 0
})

const showNext = ref(false)

const handleButtonClick = async (val) => {
  if (isResult.value) {
    return
  }

  isCommonSense.value = val
  await submit()
}


const submit = async () => {
  if (userAnswer.value === 0 || isCommonSense.value === 0) {
    return
  }

  const quizSubmit = ({
    quizId: quiz.value.quizId,
    userAnswer: userAnswer.value,
    isCommonSense: isCommonSense.value,
  })

  if (user.isLoggedIn) {
    await postQuizSubmit(quizSubmit)
  }

  const data = await getQuizResult(quiz.value.quizId)

  if (data === null) {
    await router.push({ name: 'home' })
  }

  result.value.answer = data.answer
  result.value.userAnswers = data.userAnswers
  result.value.isCommonSense = data.isCommonSense
  result.value.notCommonSense = data.notCommonSense
  result.value.total = data.isCommonSense + data.notCommonSense

  if (!user.isLoggedIn) {
    result.value.total++
    result.value.userAnswers[userAnswer.value - 1]++
    isCommonSense.value ? result.value.isCommonSense++ : result.value.notCommonSense++
  }

  isResult.value = true
}

const setQuiz = async () => {
  const data = router.currentRoute.value.name === 'home' ? await getRandomQuiz() : await getQuiz(route.params.quizId)
  quiz.value.quizId = data.quizId
  quiz.value.content = data.content
  quiz.value.options[0] = data.option1
  quiz.value.options[1] = data.option2
  data.option4 ? quiz.value.options[3] = data.option4 : quiz.value.options.length = 3
  data.option3 ? quiz.value.options[2] = data.option3 : quiz.value.options.length = 2
}

const handleShowNextClick = async () => {
  if (router.currentRoute.value.name == 'quiz') {
    await router.push({ name: 'home'})
    return
  }

  await setQuiz()
  userAnswer.value = 0
  isCommonSense.value = 0
  isResult.value = false
}

onMounted(async () => {
  await setQuiz()
})

watch(() => isResult.value, async (newValue) => {
  if (newValue === true) {
    setTimeout(() => {
      showNext.value = true
    }, 2500)
  }
  if (newValue === false) {
    await setQuiz()
    showNext.value = false
  }
})

</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: center;

  width: 40%;
  padding: 20px;
  min-width: 450px;
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(50px);
}

.no-quiz {
  padding: 30px 0;
  font-size: 36px;
  font-weight: 1000;
}

.go-create {
  margin: 20px 0;
  text-align: center;
  line-height: 52px;
  width: 60%;
  height: 52px;
  border-radius: 4px;
  background-color: #000000;
  color: #ffffff;
  font-size: 16px;
}

.go-create:hover {
  background-color: rgba(0, 0, 0, 0.8);
}

.this-is {
  font-size: 20px;
  width: 100%;
}

.line {
  border: 1px solid #ffffff;
  width: 100%;
}

.quiz-buttons {
  display: flex;
  gap: 20px;
  width: 100%;
}

.show-next {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  width: 100vw;
  height: 100vh;
  cursor: pointer;
}
</style>
