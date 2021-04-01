<template>
<div>
  <head>
    <title> User Management </title>
  </head>

    <div class="navbarContainer">
      <AdminNavbar/>
    </div>
    <h2 style="text-align: center"> User Management </h2>
    <div id="CustomerList" class = "column">
    <h4>Customers</h4>
      <table>
        <thead>
          <tr id ="header">
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="customer in customers" @click="selectRow(customer)" v-bind:key="customer.username" :class="{'highlight': (customer.username === selectedUsername)}">
            <td>{{ customer.username }}</td>
            <td>{{ customer.name }}</td>
            <td>{{ customer.email }}</td>
          </tr>
          <tr v-for="n in emptyCustomers" v-bind:key="n">
          <td></td>
          <td></td>
          <td></td>
          </tr>
        </tbody>
      </table>
      <div>
          <button
            type="button" 
            v-bind:disabled= "(selectedUserType !== 'customer')"
            class="inline"
            id="delete-button"
            @click ="deleteAccount(selectedUsername)">Delete Account
          </button>
          <button
            type="button"
            v-bind:disabled= "(selectedUserType !== 'customer')"
            class="inline"
            id="makeTechnician-button"
            @click ="makeTechnician(selectedUsername)">Make Technician
          </button>
          <button
            type="button"
            v-bind:disabled= "(selectedUserType !== 'customer')"
            class="inline"
            id="makeAdmin-button"
            @click ="makeAdmin(selectedUsername)">Make Admin
          </button>
      </div>
    </div>

    <div id="TechnicianList" style="overflow-y:auto;" class = "column">
      <h3>Technicians</h3>
      <table>
        <thead>
          <tr id = "header">
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="technician in technicians" @click="selectRow(technician)" v-bind:key="technician.username" :class="{'highlight': (technician.username === selectedUsername)}">
            <td>{{ technician.username }}</td>
            <td>{{ technician.name }}</td>
            <td>{{ technician.email }}</td>
          </tr>
          <tr v-for="n in emptyTechnicians" v-bind:key="n">
          <td></td>
          <td></td>
          <td></td>
          </tr>
          </tbody>
      </table>
      <div id = "buttons">
          <button
            type="button"
            v-bind:disabled= "(selectedUserType !== 'technician')"
            class="inline"
            id="delete-button"
            @click ="deleteAccount(selectedUsername)">Delete Account
          </button>
      </div>
    </div>

    <div id="AdministratorList" style="overflow-y:auto;" class = "column">
      <h3>Administrators</h3>

      <table>
        <thead>
          <tr id ="header">
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="administrator in administrators" @click="selectRow(administrator)" v-bind:key="administrator.username" :class="{'highlight': (administrator.username === selectedUsername)}">
            <td>{{ administrator.username }}</td>
            <td>{{ administrator.name }}</td>
            <td>{{ administrator.email }}</td>
          </tr>
          <tr v-for="n in emptyAdministrators" v-bind:key="n">
          <td></td>
          <td></td>
          <td></td>
          </tr>

        </tbody>
      </table>

      <div>
        <button
          type="button"
          v-bind:disabled= "(selectedUserType !== 'administrator')"
          class="inline"
          id="delete-button"
          @click ="deleteAccount(selectedUsername)">Delete Account
        </button>
      </div>
    </div>

</div>
</template>


<style>

.inline {
  display:inline-block;
}

table {
  border-spacing: 0px;
  border-collapse: collapse;
  border: 1px solid black;
  width: 90%;
  margin-left: auto;
  margin-right: auto;
}

thead {
  display: block;
}

th {
  border: 1px solid black;
  text-align: center;
  border-bottom: none;
  width: 33.33%;
  min-width: 100px;
}

tbody {
  display: block;
  height: 200px;
  overflow-y: scroll;
}
tr {
  display: flex;
  height: 40px;
}

td {
  border: 1px solid black;
  text-align: center;
  border-bottom: none;
  width: 33.33%;

}

tr:nth-child(odd) {background-color: lightgrey}

tr.highlight {
  background-color: yellow;
}

.column {
  float:left;
  width: 33.33%;
  text-align: center;
}

#header {
  background: white;
  width: calc(100% - 17px);
}

.navbarContainer {
  margin-bottom: 0px;
}

</style>

<script>
import AdminNavbar from '@/components/AdminNavbar'

import axios from "axios";
var config = require("../../config");
var frontendUrl = "https://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { "Access-Control-Allow-Origin": frontendUrl },
})

export default {

  data() {
    return {
      customers: [],
      administrators: [],
      technicians: [],
      selectedUsername: null,
      selectedUserType: null,
    }
  },

  created: function() {
    this.fetch()
  },

  components: {
    AdminNavbar
  },

  computed: {
    emptyCustomers() {
      if(typeof(this.customers) == "undefined") {
        return 5;
      }
      return Math.max(5 - this.customers.length,0);
    },
    emptyTechnicians() {
      if(typeof(this.technicians) == "undefined") {
        return 0;
      }
      return Math.max(5-this.technicians.length,0);
    },
    emptyAdministrators() {
      if(typeof(this.administrators) == "undefined") {
        return 0;
      }
      return Math.max(5-this.administrators.length,0);
    },
  },

	methods: {
		fetch (){
			AXIOS.get('/appointments/')
      .then(response => {
        this.appointments = response.data
      })
      .catch(e => {
        var error = e.response.data
        console.log(error)
        this.endUserError = error
        document.write(error)
      }),

      AXIOS.get('/administrators/')
      .then(response => {
        this.administrators = response.data
      })

      .catch(e => {
        var error = e.response.data
        console.log(error)
        this.endUserError = error
        document.write(error)
      }),

      AXIOS.get('/technicians/')
      .then(response => {
        this.technicians = response.data;
        if(typeof(technicians) !== 'undefined') {
          if(technicians.length < 5) {
          emptytechnicians = 5 - technicians.length;
          }
        }
      })
      .catch(e => {
        var error = e.response.data
        console.log(error)
        this.endUserError = error
        document.write(error)
      })
    },
    deleteAccount(username) {
      AXIOS.delete('/endUsers/' + username + '/')
      .then(response => 
      { this.fetch() })

    },

    makeTechnician(username) {
      AXIOS.post('/make/' + username + '/technician/')
      .then(response => 
      { this.fetch() })
    },

    makeAdmin(username) {
      AXIOS.post('/make/'+ username + '/administrator/')
      .then(response => 
      { this.fetch() })
    },

    selectRow(user) {
      this.selectedUsername = user.username;
      this.selectedUserType = user.userType;
    }



  }
}

</script>
