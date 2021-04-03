<template>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<img class="logo" src="https://i.ibb.co/kcj0HD4/circle-cropped-1.png" width="70">
	
	<div class="container-fluid">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" href="#/AdministratorHome">Home</a>
        </li>
        <li class="nav-item">
          <router-link class="nav-link active" to="/AdminUserManagement">User Management</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link active" to="/AdminTechnicianSchedule">Technician Schedule</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link active" to="/">Log Out</router-link>
        </li>
      </ul>
    </div>
  </div>
</nav>
</template>
<style>
</style>
<script>
export default {
	name: "AdminNavbar",
}
</script>