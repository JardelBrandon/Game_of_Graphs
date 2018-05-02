var bannerboy = bannerboy || {};
bannerboy.main = function() {

	var width = 970;
	var height = 90;
	var banner = bannerboy.createElement({id: "banner", width: width, height: height, backgroundColor: "#fff", overflow: "hidden", cursor: "pointer", parent: document.body});
	var border = bannerboy.createElement({width: "100%", height: "100%", border: "1px solid #d5d5d5", boxSizing: "border-box", zIndex: 10, parent: banner});
	var main_tl = new BBTimeline();

	var images = [	
		"txt_1.png", 
		"logo_gsuite.png", 
		"icon_gmail.png", 
		"icon_documents.png", 
		"icon_gdrive.png", 
		"icon_calendar.png", 
		"cta_txt.png", 
		"cloud.png", 
		"logo_gsuite_big.png",
	];

	bannerboy.preloadImages(images, function() {

		/* Create elements
		================================================= */

		var logo_gsuite = bannerboy.createElement({backgroundImage: "logo_gsuite.png", left: 820, top: 33, retina: true, parent: banner});
		var illustration = bannerboy.createElement({left: 470, top: 10, width: 135, retina: true, parent: banner});
		var txt_1 = bannerboy.createElement({backgroundImage: "txt_1.png", left: 50, top: 25, retina: true, parent: banner});
		var lockupContainer = bannerboy.createElement({left: 142, top: 34, width: 110, height: 24, parent: banner});
			var icon_gmail = bannerboy.createElement({backgroundImage: "icon_gmail.png", top: 7, retina: true, parent: lockupContainer});
			var icon_documents = bannerboy.createElement({backgroundImage: "icon_documents.png", left: 32, retina: true, parent: lockupContainer});
			var icon_gdrive = bannerboy.createElement({backgroundImage: "icon_gdrive.png", left: 58, top: 2, retina: true, parent: lockupContainer});
			var icon_calendar = bannerboy.createElement({backgroundImage: "icon_calendar.png", left: 89, top: 4, retina: true, parent: lockupContainer});
		var logo_gsuite_big = bannerboy.createElement({backgroundImage: "logo_gsuite_big.png", left: 27, top: 33, retina: true, parent: banner});
		var cta = bannerboy.createElement({left: 464, top: 26, width: 129, height: 39, parent: banner});
			var cta_base = bannerboy.createElement({backgroundColor: "#4285f3", width: 129, height: 39, parent: cta});
			var cta_txt = bannerboy.createElement({backgroundImage: "cta_txt.png", left: 17, top: 14, retina: true, parent: cta});
		var logo_cloud = bannerboy.createElement({backgroundImage: "cloud.png", left: 821, width: 109, top: 36, retina: false, parent: banner});	

		/* Adjustments
		================================================= */

		cta_base.set({borderRadius: 2});

		/* Initiate
		================================================= */
		animations();
		timeline();
		interaction();

		/* Animations
		================================================= */
		function timeline() {
			main_tl
			.offset(.25)
			.add(illustration.in)
			.offset(1)
			.add(textIn(txt_1))
			.offset(3)
			.add(txt_1.out)
			.offset(1)
			.add(banner.lockupIn)
			.offset(.75)
			.add(cta.in)
			.add(logo_cloud.in);
		
			scrubber(main_tl);
		}

		function animations() {

			illustration.in = new BBTimeline()
			.from([logo_gsuite], 0.5, {opacity: 0});

			textIn = function(txt) {
				return new BBTimeline()
				.from(txt, 0.5, {opacity: 0, ease: Power1.easeInOut});
			}

			txt_1.out = new BBTimeline()
			.to([txt_1, logo_gsuite], 0.5, {opacity: 0});

			banner.lockupIn = new BBTimeline()
			.staggerFrom([icon_gmail, icon_documents, icon_gdrive, icon_calendar].reverse(), 1, {cycle: {x: function (i) { return -3 * bannerboy.utils.map((i+1), 1, 5, 5, 1); }}, ease: Power2.easeOut}, 0.2)
			.staggerFrom([icon_gmail, icon_documents, icon_gdrive, icon_calendar].reverse(), 0.3, {opacity: 0}, 0.2)
			.offset(0.5)
			.from(logo_gsuite_big, 1, {x: 10, opacity: 0});

			logo_cloud.in = new BBTimeline()
			.from(logo_cloud, 1, {opacity: 0, y: 10, ease: Power2.easeOut});

			cta.in = new BBTimeline()
			.from(cta, 1, {opacity: 0, y: 10, ease: Power2.easeOut});

		}


		/* Interactions
		================================================= */
		function interaction() {
			// click logic goes here
			banner.onclick = function() {
				Enabler.exit("Clickthrough");
			};
		}

		/* Helpers
		================================================= */

		/* Scrubber
		================================================= */
		function scrubber(tl) {
			if (window.location.origin == "file://") {
				bannerboy.include(["../bannerboy_scrubber.min.js"], function() {
					if (bannerboy.scrubberController) bannerboy.scrubberController.create({"main timeline": tl});
				});
			}
		}
	});
};