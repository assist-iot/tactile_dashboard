<template>
	<div class="dashboard-container" v-if="URL">
		<iframe :src="URL" width="100%" height="100%" frameborder="0" />
	</div>
	<pui-form-loading v-else></pui-form-loading>
</template>

<script>
export default {
	name: 'DashboardIframe',
	data() {
		return {
			URL: undefined,
			BASE_URL : '/puivariable/getVariable'
		};
	},
	beforeMount() {
		// hide core header
		setTimeout(() => {
			document.getElementsByClassName('v-main__wrap')[0].firstElementChild.style.display = 'none';
		}, 10);
	},
	beforeDestroy() {
		// show core header
		document.getElementsByClassName('v-main__wrap')[0].firstElementChild.style.display = 'block';
	},

	created() {
		this.getVariable();
	},

  watch: {
    $route() {
      this.getVariable(); 
    }
  },	

	methods: {
		formatToVariableURl(functionality) {
			let variable = `${functionality}_URL`;
			variable = variable.toUpperCase(); 
			return variable.replace(/-/g, '_'); 
		},

		getVariable() {
			const FUNCTIONALITY = this.$route.path;
			this.$puiRequests.getRequest(`${this.BASE_URL}/${this.formatToVariableURl(FUNCTIONALITY)}`,
			null,
			(response) => {
				this.URL = response.data;
				console.log(this.URL)
			},
			() => {}
		);			
		}
	},
};
</script>

<style>
.dashboard-container {
	height: 99vh;
}
</style>
