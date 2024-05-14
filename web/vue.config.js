const DASHBOARD_HOST_NAME = (process.env.DASHBOARD_HOST_NAME) ? process.env.DASHBOARD_HOST_NAME : 'localhost';
const DASHBOARD_HOST_PORT = (process.env.DASHBOARD_HOST_PORT) ? process.env.DASHBOARD_HOST_PORT : 8080;
const ENABLE_AUTH_IDM = (process.env.ENABLE_AUTH_IDM) ? process.env.ENABLE_AUTH_IDM : false; 

module.exports = {
	transpileDependencies: ['vuetify'],

	configureWebpack: {
		devtool: 'source-map'
	},

	chainWebpack: (config) => {
		config.plugin('html').tap((args) => {
			args[0].title = 'ASSIST-IoT Dashboard';
			return args;
		});
	},

	devServer: {
		port: 8081,
		watchOptions: {
			ignored: [/node_modules/],
			aggregateTimeout: 300,
			poll: 300,
		},

		proxy: {
			'^/dashboard': {
				target: `http://${DASHBOARD_HOST_NAME}:${DASHBOARD_HOST_PORT}`,
				changeOrigin: true, 
				...(ENABLE_AUTH_IDM ? {
					pathRewrite: {
						'^/dashboard/login/signin': '/dashboard/loginAutzIdm/signin'
					}
				} : {}),
			} 
		},
	},

	publicPath: '/assistiot'
};