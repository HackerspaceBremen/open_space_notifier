<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<footer class="row">
		<div class="small-12 columns">
			<hr />
			<div class="row">
				<div class="small-6 columns">
					<p>Copyright &copy; 2013-14 Hackerspace Bremen</p>
				</div>
				<div class="small-6 columns">
					<ul class="inline-list right">
						<li><a href="https://github.com/HackerspaceBremen/open_space_notifier" target="_blank">Code on Github</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
	<!-- End Footer -->

	<!-- Check for Zepto support, load jQuery if necessary -->
	<script>
	  document.write('<script src=/javascripts/vendor/'
	    + ('__proto__' in {} ? 'zepto' : 'jquery')
	    + '.js><\/script>');
	</script>
	
	<script src="/javascripts/foundation.min.js"></script>
	<script src="/javascripts/foundation/foundation.topbar.js"></script>
	
	<script>
	  $(document).foundation();
	</script>
