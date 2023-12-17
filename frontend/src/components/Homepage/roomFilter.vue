<template lang="">
    <div>
        <!-- 房型筛选 -->
        <div class="filter">
          <div class="selections">
            <label style="label">房型: </label>
            <text
              type="text"
              class="textbotton"
              :class="{ active: activeroom === 'allroom' }"
              @click="roomActive('allroom')"
              >全部</text
            >
            <el-button
              type="text"
              class="textbotton"
              :class="{ active: activeroom === 'room1' }"
              @click="roomActive('room1')"
              >一室</el-button
            >
            <el-button
              type="text"
              class="textbotton"
              :class="{ active: activeroom === 'room2' }"
              @click="roomActive('room2')"
              >二室</el-button
            >
            <el-button
              type="text"
              class="textbotton"
              :class="{ active: activeroom === 'room3' }"
              @click="roomActive('room3')"
              >三室</el-button
            >
            <el-button
              type="text"
              class="textbotton"
              style="width:15px;"
              :class="{ active: activeroom === 'room4' }"
              @click="roomActive('room4')"
              >三室以上</el-button
            >
          </div>
        </div>
    </div>
</template>
<script>
import "./Homepage.css";
export default {
    data(){
        return {
            activeroom: "allroom",
        }
    },
    methods:{
     roomActive(botton) {
      this.activeroom = this.activeroom === botton ? "allroom" : botton;
    },
    }
}
</script>
<style lang="">

</style>