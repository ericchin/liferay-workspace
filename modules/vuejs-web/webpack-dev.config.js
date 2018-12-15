var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
	entry: './src/main/resources/META-INF/resources/js/index.es.js',
	output: {
		path: path.resolve(__dirname, './build/resources/main/META-INF/resources/js/'),
		publicPath: '/',
		filename: 'index.es.js'
	},
	module: {
		rules: [
			{
				test: /\.css$/,
				use: [
					'vue-style-loader',
					'css-loader'
				],
			},
			{
				test: /\.vue$/,
				loader: 'vue-loader',
				options: {
					loaders: {
						// Since sass-loader (weirdly) has SCSS as its default parse mode, we map
						// the "scss" and "sass" values for the lang attribute to the right configs here.
						// other preprocessors should work out of the box, no loader config like this necessary.
					}
					// other vue-loader options go here
				}
			},
			{
				test: /\.es.js$/,
				loader: 'babel-loader',
				exclude: /node_modules/
			},
			{
				test: /\.(png|jpg|gif|svg)$/,
				loader: 'file-loader',
				options: {
					name: '[name].[ext]?[hash]'
				}
			}
		]
	},
	performance: {
		hints: false
	},
	devtool: 'inline-source-map',
	devServer: {
		host: '0.0.0.0',
		port: 3000,
		compress: true,
		contentBase: './build/resources/main/META-INF/resources/js'
	},
	plugins: [
		new HtmlWebpackPlugin({
			inject: false,
			template: require('html-webpack-template'),
			headHtmlSnippet: '<script type="text/javascript" src="/index.es.js"></script>',
			bodyHtmlSnippet: '<div id="test-1"></div><div id="test-app"></div><script type="text/javascript">sampleVueApp.render(\'test\');</script>',
			excludeChunks: [
				'main'
			]
		})
	]
};
