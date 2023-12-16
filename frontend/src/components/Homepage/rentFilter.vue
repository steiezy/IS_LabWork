<template lang="rentFilter">
   
    <!-- 月租筛选 -->
    <div class="filter">
      <div style="display: grid">
        <div class="selections">
          <label class="label">月租: </label>
          <text
            type="text"
            class="textbotton"
            :class="{ active: activerent === 'allrent' }"
            @click="rentActive('allrent')"
            >全部</text
          >
          <text
            type="text"
            class="textbotton"
            :class="{ active: activerent === 'rent1' }"
            @click="rentActive('rent1')"
            >1000元以下</text
          >
          <text
            type="text"
            class="textbotton"
            :class="{ active: activerent === 'rent12' }"
            @click="rentActive('rent12')"
            >1000-2000元</text
          >
          <text
            type="text"
            class="textbotton"
            :class="{ active: activerent === 'rent23' }"
            @click="rentActive('rent23')"
            >2000-3000元</text
          >
          <text
            type="text"
            class="textbotton"
            :class="{ active: activerent === 'rent34' }"
            @click="rentActive('rent34')"
            >3000-4000元</text
          >
          <text
            type="text"
            class="textbotton"
            :class="{ active: activerent === 'rent4' }"
            @click="rentActive('rent4')"
            >4000元以上</text
          >

          <label></label>
        </div>
        <div class="selectRent">
          <label class="label">预算： </label>
          <el-input
            v-model.number="lowlevel"
            placeholder=""
            class="input"
          />
          <div>-</div>
          <el-input
            v-model.number="highlevel"
            placeholder=""
            class="input"
          />
          <button style="height: 30px">查询</button>
        </div>
      </div>
    </div>
    

</template>
<script>
import './Homepage.css';
export default {
data() {
    return {
        activerent: "allrent",
    }
},
methods: {
    rentActive(botton) {
    this.activerent = this.activerent === botton ? "allrent" : botton;
    },
}

}
</script>

<style lang="">

</style>