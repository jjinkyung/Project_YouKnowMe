<template>
  <div >
    <form action="post" @submit.prevent="admin.addBalance(balanceValue)">
      <hgroup class="add-bubble">
      <h2 id="add-title">밸런스 게임 추가</h2>
      <div>
        <div class="create">
          <input class="content" type="text" v-model="balanceValue.question" placeholder="question을 입력해주세요."/>
        </div>
        <div class="create">
          <input class="content" type="text" v-model="balanceValue.answer1" placeholder="answer1을 입력해주세요." />
        </div>
        <div class="create">
          <input class="content" type="text" v-model="balanceValue.answer2" placeholder="answer2를 입력해주세요."/>
        </div>
      </div>
        <button class="admin-btn admin-balance-btn">추가</button>
      </hgroup>
    </form>
  </div>
  
</template>

<script>
import {useAdminStore} from "@/stores/admin/admin"
import { ref } from "vue"

  export default {
    name: "BalanceForm",
    setup() {
      const admin = useAdminStore();
      const balanceValue = ref({
        seq:'',
        question: '',
        answer1: '',
        answer2: ''
      })
      return {
        admin,
        balanceValue
      }
    },
    methods: {
      submit() {
        const {question, answer1, answer2} = this;
        if(!question || !answer1 || !answer2){
          alert("모든 항목을 입력해주세요.")
        }
        this.$emit('submit', {question, answer1, answer2 })
      }
    }
  }

</script>


<style>
.title {
  font-weight: 600;
}
.create {
  margin: 15px 0px 15px;
}
.content {
  margin-top: auto;
  width: 100%;
  height: 30px;
  font-size: large;
  font-weight: 600;
  border-radius: 10px;
}
.balance-form {
  width: 100%;
  background-color: pink;
}
.admin-balance-btn {
  /* position: fixed; */
  bottom: 1%;
  right: 1%;
  width: 100px;
  height: 40px;
  margin: 10px;
}
.admin-balance-btn:hover {
  background-color: #c1c3fc;
}
.add-bubble {
  /* position: absolute; */
  padding: 30px 10px 30px 10px;
  right: 50px;
  bottom: 300px;
  background: #f0f1ff;
  border-radius: 20px;
  width: 300px;
  height: 300px;
  margin: 0;
  text-align: center;
  line-height: 150%;
  color: gray;
  font-weight: bold;
  text-shadow: 0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25);
  filter: drop-shadow(0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25));
  animation-name: bubbleAni;
  animation-duration: 3s;
  animation-iteration-count: infinite;
}

.add-bubble:after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 0;
  border: 24px solid transparent;
  border-top-color: #f0f1ff;
  border-bottom: 0;
  border-left: 0;
  margin-left: 20px;
  margin-bottom: -20px;
}
</style>