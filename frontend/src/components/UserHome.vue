<template>
    <h1>Home</h1>
</template>


<script>
export default {
    name: 'UserHome',
    data() {
        return {
        }
    },

    methods: {
    },
    mounted() {
        if (!localStorage.getItem('token')) {
            this.$router.push('/sign');
        }
    }

}
</script>-


