var path = require('path');
var webpack = require('webpack');

module.exports = {
	entry: './src/main/resources/META-INF/resources/js/index.es.js',
	output: {
		path: path.resolve(__dirname, './build/resources/main/META-INF/resources/js/'),
		publicPath: '/o/vuejs-web/js/',
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
	}
};
