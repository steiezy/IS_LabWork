<template lang="">
    <div>

        <div v-if="houses.length == 0">
        <el-empty>
            <el-button type="primary" @click="addHouse()">Add Your First House</el-button>
        </el-empty>
        </div>
        <div class="order-list" v-else>
          <!-- 信息展示栏 -->
          <section class="orders">
            <div class="listing" v-for="house in houses" :key="house.id">
              <img :src="order.houseDAO.photos[0].fileurl"  class="photo"/>
              <div class="details">
                <h2>{{ house.houseDAO.title }}</h2>
                <p>{{ house.houseDAO.location}}</p>
                <p>{{ house.houseDAO.proportion }} 平方米</p>
                <div>{{ house.houseDAO.price }} 元/月</div>
              </div>
              <el-button type="primary">Edit</el-button>
            </div>
          </section>
        </div>
        <div>
        <!-- Add House Dialog -->
        <el-dialog title="Add House" v-model="showAddHouseDialog">
            
            
        </el-dialog>
        </div>


    </div>
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            showAddHouseDialog: false,
            houses: [],
            userId: null,
            isEmpty: true, // 控制 el-empty 组件是否显示
        }
    },
    methods: {
        addHouse() {
            this.showAddHouseDialog = true;
        },
        getHouseOrders(){
            axios.get(`${this.$store.state.server}/${this.$store.state.user}/userId=${this.userId}/houses`)
            .then(res => {
                this.houses = res.data;
            })
            .catch(err => {
                console.log(err);
            })
        },
        
        // 判断是否为空

    },
    beforeMount(){
        this.userId = localStorage.getItem('userId');
        this.getHouseOrders();

    }
}

</script>
<style lang="">
    
</style>