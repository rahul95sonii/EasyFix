function initMap() {
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 12,
    center: {lat: 28.6139391, lng: 77.209021}
  });
  var geocoder = new google.maps.Geocoder();

  document.getElementById('geoCode').addEventListener('click', function() {
    geocodeAddress(geocoder, map);
  });
}

function geocodeAddress(geocoder, resultsMap) {
  var addr = document.getElementById('address').value;
  var city = document.getElementById("cityId");
  var cityText = city.options[city.selectedIndex].text;
  if(addr == "") { alert("Please enter address");}
  else if(city.value == "")  { alert("Please select city");}
  else {
	  var address = addr+", "+cityText;
	  geocoder.geocode({'address': address}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      resultsMap.setCenter(results[0].geometry.location);
	      var marker = new google.maps.Marker({
	        map: resultsMap,
	        position: results[0].geometry.location
	      });
		  document.getElementById('coord').value = results[0].geometry.location;
	    } else {
	      alert('Geocode was not successful for the following reason: ' + status);
	    }
	  });
  }
}