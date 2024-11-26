<template>
  <div class="option-input">
    <input type="text" :value="value" :placeholder="placeholder" />
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

defineProps({
  value: String,
  placeholder: String
})

</script>

<style scoped>
input {
  padding: 12px;
  font-size: 16px;
  border: 2px solid #ffffff;
  border-radius: 4px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 50px;
}

input:focus {
  box-shadow: 0 0 10px #616161;
  outline: 0;
}

input::placeholder {
  font-size: 16px;
}
</style>
