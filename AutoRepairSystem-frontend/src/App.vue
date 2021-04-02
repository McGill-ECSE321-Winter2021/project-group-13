<template>
  <div id="app">
    <img class="logo" src="https://i.ibb.co/kcj0HD4/circle-cropped-1.png" width="100">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>

@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap');

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background: white;
  
}

.logo{
	left: 0;
	margin: 10px;
	text-align: left;
}

@import'~bootstrap/dist/css/bootstrap.css'
</style>
