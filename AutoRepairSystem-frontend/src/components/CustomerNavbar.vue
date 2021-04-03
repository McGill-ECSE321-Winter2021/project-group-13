<template>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<img class="logo" src="https://i.ibb.co/kcj0HD4/circle-cropped-1.png" width="70">
	
	<div class="container-fluid">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" href="#/CustomerHome">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="#/AvailableAppointments">Appointments</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="#/PayInvoice">Payment</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active"  href="#/">Log Out</a>
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
	name: "CustomerNavbar",
}
</script>