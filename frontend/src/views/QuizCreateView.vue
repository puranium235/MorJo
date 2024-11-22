<template>
  <div class="container">
    <div class="content-field">
      <label for="content" class="content">문제 내용</label>
      <textarea rows="1" type="text" :value="content" placeholder="문제를 입력하세요" @input="handleContentInput"
        @keydown.enter.prevent class="content-input" />
    </div>
    <hr />

    <div v-for="(option, index) in options" :key="index" class="option-field">
      <div class="option-input-wrapper">
        <input type="radio" :value="index + 1" v-model="answer" :id="`option${index}`" hidden />
        <label :for="`option${index}`" class="answer-button" :class="{ selected: answer === index + 1 }">
          <i :class="answer === index + 1 ? 'bi bi-check-square' : 'bi bi-square'"></i>
        </label>
        <quiz-create-option :value="option" :placeholder="`선택지${index + 1}을 입력하세요`"
          @input="handleOptionInput(index, $event)"></quiz-create-option>
        <button v-if="options.length > 2 && index === options.length - 1" @click="handleOptionRemove(index)"
          class="bi bi-dash-square remove-button"></button>
      </div>
    </div>

    <i v-if="options.length < 4" class="bi bi-plus-lg add-button" @click="handleOptionAdd"></i>

    <div class="submit-button">
      <button @click="handleQuizSubmit">등록</button>
    </div>
  </div>
  <div v-show="errorMessage" class="alert">
    {{ errorMessage }}
  </div>
</template>

<script setup>
import { ref } from "vue";
import QuizCreateOption from "@/components/quiz/QuizCreateOption.vue";
import { postQuizCreate } from "@/api/quizApi";
import router from "@/router";

const content = ref("")
const options = ref(["", ""])
const answer = ref(0)
const errorMessage = ref("")

const handleOptionAdd = () => {
  if (options.value.length < 4) {
    options.value.push("")
  }
};

const handleOptionRemove = (index) => {
  options.value.splice(index, 1)

  if (answer.value === index + 1) {
    answer.value = 0
  }
};

const handleQuizSubmit = async () => {
  if (content.value.trim() === '') {
    errorMessage.value = "문제 내용을 입력해주세요."
    return
  }

  for (const option of options.value) {
    if (option.trim() === '') {
      errorMessage.value = "모든 선택지 내용을 입력해주세요."
      return
    }
  }

  if (!answer.value) {
    errorMessage.value = "정답을 선택해주세요."
    return
  }

  errorMessage.value = ""

  const quiz = {
    content: content.value.trim(),
    option1: options.value[0].trim(),
    option2: options.value[1].trim(),
    option3: options.value[2] ? options.value[2].trim() : null,
    option4: options.value[3] ? options.value[3].trim() : null,
    answer: answer.value,
  }

  try {
    const data = await postQuizCreate(quiz)
    await router.push({ name: 'quiz', params: {quizId: data} })
  } catch (error) {
    errorMessage.value = '퀴즈 등록 중 오류가 발생했습니다.'
  }
}

const autoResize = (event) => {
  const textarea = event.target
  textarea.style.height = "auto"
  textarea.style.height = `${textarea.scrollHeight}px`
}

const checkContentLength = () => {
  const maxLength = 255

  if (content.value.length > maxLength) {
    content.value = content.value.slice(0, maxLength)
    errorMessage.value = '문제 내용의 글자 수 제한을 넘겼습니다.'
    return
  }

  errorMessage.value = ''
}

const handleContentInput = (event) => {
  autoResize(event)
  content.value = event.target.value
  checkContentLength()
}

const checkOptionByte = (index) => {
  const maxByte = 32
  const byteLength = new TextEncoder().encode(options.value[index]).length

  if (byteLength > maxByte) {
    const characters = options.value[index]
    let validText = ''
    let totalByte = 0
    let flag = true

    for (let character of characters) {
      const decimal = character.charCodeAt(0)
      const byte = (decimal >> 7) ? 2 : 1

      if (totalByte + byte > maxByte) {
        errorMessage.value = '선택지의 글자 수 제한을 넘겼습니다.'
        flag = false
        break
      }

      validText += character
      totalByte += byte
    }

    if (flag) {
      errorMessage.value = ''
    }
    options.value[index] = validText
  }
}

const handleOptionInput = (index, event) => {
  options.value[index] = event.target.value
  checkOptionByte(index)
}
</script>
<style scoped>
.option-input-wrapper input[type="radio"] {
  display: none;
}

.answer-button {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}

.answer-button.selected {
  color: #1ed71e;
}

.content {
  font-size: 20px;
}

.container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 40%;
  padding: 20px;
  border: 1px solid #000000;
  min-width: 450px;
}

.content-field {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.content-input {
  padding: 12px;
  font-size: 20px;
  border: 1px solid;
  width: 100%;
  resize: none;
  height: 52px;
  overflow-y: hidden;
}

.content-input::placeholder {
  font-size: 20px;
}

.option-field {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
}

.option-input-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  position: relative;
  width: 100%;
}

.quiz-create-option {
  flex-grow: 1;
}

.remove-button {
  cursor: pointer;
  font-size: 26px;
  color: #000000;
  position: absolute;
  right: 10px;
}

.remove-button:hover {
  color: #ff1828;
}

.add-button {
  font-size: 26px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
}

.add-button:hover {
  color: #1ed71e;
  border: 1px solid #e4e3e3;
}

.submit-button {
  display: flex;
  justify-content: center;
  width: 100%;
}

.submit-button button {
  font-size: 16px;
  background-color: #000000;
  color: white;
  border: none;
  cursor: pointer;
  width: 100%;
  height: 46px;
}

.submit-button button:hover {
  color: black;
  background-color: white;
  border: 1px solid;
}

.answer-button {
  font-size: 24px;
}

.alert {
  color: #ff1828;
  font-size: 24px;
  padding-bottom: 64px;
}
</style>
