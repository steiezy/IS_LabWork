<template>
  
    <!-- 类型筛选 -->
    <div class="filter">
      <label style="label">类型: </label>
      <div style="margin: 10px; width: 100px;">
        <el-select
        v-model="selectedtype"
        class="m-2"
        placeholder="租房类型"
      >
      <el-option
        v-for="item in renttypes"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
      </el-select>
      
      </div>
      <div style="margin: 5px;width: 100px;">
        <el-select
        v-model="selectedface"
        
        class="m-2"
        placeholder="租房类型"
        
      >
        <el-option
          v-for="item in facetypes"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      </div>
   
    
 
    </div>

</template>

<script>
import "./Homepage.css";

export default {
  data() {
    return {
      selectedtype: "全部",
      selectedface: "全部", // 添加选中类型的数据绑定
      renttypes: [
        {
          value: "alltype",
          label: "全部",
        },
        {
          value: "co-renting",
          label: "合租",
        },
        {
          value: "Option3",
          label: "整租",
        },

      ],
      facetypes: [
        {
          value: "allface",
          label: "全部",
        },
        {
          value: "facesouth",
          label: "朝南",
        },
        {
          value: "facenorth",
          label: "朝北",
        },
        {
          value: "facewest",
          label: "朝西",
        },
        {
          value: "faceeast",
          label: "朝东",
        },


      ],
    };
  },
};
</script>

<style scoped>
/* 添加一些样式，如果需要的话 */
</style>
