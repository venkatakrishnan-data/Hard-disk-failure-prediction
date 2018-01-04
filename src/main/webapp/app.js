$(document).ready(function() {
	$response = $('#responseField');

	function predict() {
		var data = {};

		$('input').each(function() {
			var $field = $(this);
			data[$field.attr('data-column')] = $field.val();
		});

		// $('select').each(function(){
		// var $field = $(this);
		// data[$field.attr('data-column')] = $field.val();
		// });

		var xhr = $.getJSON('/predict', data).done(function(data) {
			console.log(data);
			
			var dataLable =data.label;
			window.onload = function() {
   var getInput = dataLable;
   localStorage.setItem("storageName",getInput);
}
			var d = new Date();
			var hr = d.getHours()+":"+d.getMinutes();
			document.getElementById("time").innerHTML = hr;
			
			for (i = 1; i < 13; i++)
    			document.getElementById(i).value = (data.label.split("---")[1]).split("-")[i-1];
    			
			if (data.label.split("---")[0] === '1') {
        // alert(data.label.split("---")[0]);
				$response.text('Status: OK').css("color", "Green");

			} else {

				$response.text('Status: Critical').css("color", "Red");

			}
		}).fail(function(error) {
			console.error(error.responseText ? error.responseText : error);
			$response.text('(Invalid input)');
		})
	}

	var updatePrediction = _.debounce(predict, 250);

	$('input').each(function() {
		$(this).keydown(updatePrediction);
	});

	// $('select').each(function(){
	// $(this).change(updatePrediction);
	// });

	$("form").bind("keypress", function(e) {
		if (e.keyCode == 13) {
			return false;
		}
	});

	updatePrediction();

});
