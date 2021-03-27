<template>
<div>
  <div id="AdminUserManagement">
  <h2>Users</h2>
    <table class="styled-table">
    <thead>
        <tr>
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody>
      <tr v-for="endUser in endUsers" v-bind:key="endUser.username">
        <td>{{ endUser.username }}</td>
        <td>{{ endUser.name }}</td>
        <td>{{ endUser.email }}</td>
      </tr>
    </tbody>
</table>

  </div>
</div>
</template>


<style>
#AdminUserManagement {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>

<script>
import axios from 'axios'

var config = require("../../config");
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { "Access-Control-Allow-Origin": frontendUrl },
})

export default {

  data() {
    return {
      endUsers: []
    }
  },

  created: function() {
    this.fetch()
  },

	methods: {
		fetch (){
			AXIOS.get('/endUsers/')
				.then(response => {
					this.endUsers = response.data
				})
				.catch(e => {
					var error = e.response
					e.data
					console.log(error)
					this.endUserError = error
				})
			},
		}
}

</script>
