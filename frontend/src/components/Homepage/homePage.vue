
<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div style="display: flex">
          <img class="logo" src="../../assets/logo.png">
          <div class="city-selector">
            当前城市: New York
            <select v-model="selectedCity"
              @change="changeCity">
              <option v-for="city in cities" :key="city">{{ city }}</option>
            </select>

          </div>
        </div>

        <div class="user-center">用户中心</div>

      </el-header>

      <el-main class="filters">
    
        <positionFilter></positionFilter>
        <rentFilter></rentFilter>
        <roomFilter></roomFilter>
        <renttypeFilter></renttypeFilter>
          
        <div class="order-list">
          <!-- 信息展示栏 -->
          <section class="orders">
            <div class="listing" v-for="order in orders" :key="order.id">
              <img :src="order.houseDAO.photos[0].fileurl"  class="photo"/>
              <div class="details">
                <h2>{{ order.houseDAO.title }}</h2>
                <p>{{ order.houseDAO.location}}</p>
                <p>{{ order.houseDAO.proportion }} 平方米</p>
                <div>{{ order.houseDAO.price }} 元/月</div>
              </div>
            </div>
          </section>
        </div>
      
        <div class="pagination">
      
          <el-pagination
            v-model:current-page="pageNum"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 30, 40]"
            :disabled="disabled"
            :background="True"
            layout="sizes, prev, pager, next"
            :total="1000"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import positionFilter from "./positionFilter.vue";
import rentFilter from "./rentFilter.vue";
import renttypeFilter from "./renttypeFilter.vue";
import roomFilter from "./roomFilter.vue";

import "./Homepage.css";
import axios from "axios";
export default {
  components: {
    positionFilter,
    rentFilter,
    renttypeFilter,
    roomFilter,
  },
  name: "HomePage",
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      orders: [
        
      ],
      
    };
  },
  methods: {
    async getOrder() {
      const pageNum = this.pageNum-1;
      const pageSize = this.pageSize;
      const response = await axios.get(
        `${this.$store.state.server}/${this.$store.state.lease}/unleased?page=${pageNum}&size=${pageSize}`
      );
      // console.log(response);
      
      this.orders = response.data;
      // console.log(this.orders);
    },
    handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageNum=1
        this.pageSize=val
        this.getOrder()
      },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.getOrder()
      
    },

  },
  mounted() {
    this.getOrder();
  },
};
</script>

<style scoped>
/* 这里添加你的 CSS 样式 */
</style>
