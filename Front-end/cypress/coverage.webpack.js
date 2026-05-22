module.exports = {
  module: {
    rules: [
      {
        test: /\.(ts|js)$/,
        use: {
          loader: "babel-loader",
          options: {
            plugins: ["istanbul"],
          },
        },
        enforce: "post",
        include: require("path").join(__dirname, "..", "src"),
        exclude: [/\.(e2e|spec|cy)\.ts$/, /node_modules/],
      },
    ],
  },
};
