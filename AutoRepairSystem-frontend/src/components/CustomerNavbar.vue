<template>
	<nav class="navbar navbar-expand-lg navbar-light">

<img class="logo" src="https://i.ibb.co/kcj0HD4/circle-cropped-1.png" width="70">

	<div class="container-fluid">
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
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

.navbar, .container-fluid, .navbar-collapse{
  height: 80px;
  background-color: white;
}

.navbar{box-shadow: 0 2px 4px 0 rgba(0,0,0,.2);}

</style>
<script>
export default {
	name: "CustomerNavbar",
}
</script>