//====================================================================================================================
//             This update-check.js calculates simple Semantic version which is configured like 'x.y.z'
//             the alphabetical character calculation like 'alpha' or 'beta' is excluded on this script.
//====================================================================================================================
var latestVersion = '1.3.0';		// change this value to what you want.
var jar = 'https://github.com/your-github-account/your-plugin-name/releases/download/v1.3.0/your-plugin-1.3.0.jar';
//var requiredActlist   = '1.6.0';	// fill this value if necessary.
var url = 'https://github.com/your-github-account/your-plugin-name/releases'; // change this value to what you want.
//var killSwitchUntil   = '1.1.0';	// fill this value if necessary.
//var endOfServiceUntil = '1.2.0';	// fill this value if necessary.
//====================================================================================================================


/* IIFE (Immediately Invoked Function Expression) */
(function() {
	return {
		'available': available(),
		'jar': jar,
		'requiredActlist': requiredActlist(),
		'url': url,
		'endOfService': endOfService(),
		'killSwitch': killSwitch()
	};
}());


/* The plugin's version will be invoked as 'version' variable from Actlist engine. */
var version = version;

function available() {
	return compare(latestVersion, version) > 0;
}

function requiredActlist() {
	if (typeof requiredActlist == 'undefiend') {
		return '';
	}
	
	return requiredActlist;
}

function endOfService() {
	if (typeof endOfServiceUntil == 'undefined') {
		return false;
	}
	
	return compare(version, endOfServiceUntil) < 0;
}

function killSwitch() {
	if (typeof killSwitchUntil == 'undefined') {
		return 'off';
	}
	
	return (compare(version, killSwitchUntil) < 0) ? 'on' : 'off';
}

function compare(latestVersion, version) {
	if (latestVersion == version) {
		return 0;
	}
	
	var _o1 = latestVersion.split('.');
	var _o2 = version.split('.');
	
	var o1Major = parseInt(_o1[0]);
	var o2Major = parseInt(_o2[0]);
	
	var majorCompare = o1Major.compareTo(o2Major);
	if (majorCompare == 0) {
		var o1Minor = parseInt(_o1[1]);
		var o2Minor = parseInt(_o2[1]);
		
		var minorCompare = o1Minor.compareTo(o2Minor);
		if (minorCompare == 0) {
			var o1Patch = parseInt(_o1[2]);
			var o2Patch = parseInt(_o2[2]);
			
			return o1Patch.compareTo(o2Patch);
		} else {
			return minorCompare;
		}
	}
	
	return majorCompare;
}