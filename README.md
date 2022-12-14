# springbootとopenapi

## 1.目的
Udemyでspringbootとopenapiの講義を受けたので、その際に自作したコードと参考にしたサイトのリンクを備忘録として残しておく

## 2.参考資料と自作コード
講義で使用した資料は[こちら](https://docs.google.com/presentation/d/1-x6QdqvkLDc4SWyz38Y_R6uUWZNFsSZswKQYNewmYpI/edit#slide=id.g111d5482ebd_1_67)
で、OpenApispecのバージョンは[3.0.0](https://spec.openapis.org/oas/v3.0.0#openapi-specification)
を使用した。

実際に実装したAPIは資料の76p以降に記載されている通りとなる。

また、gradleの設定(プラグインやdependenciesのバージョン)は噛み合わせが悪いとエラーがよく出る。自作した際のぷらぐいんとdependenciesを下記に記載しておく。
~~~
plugins {
	id 'org.springframework.boot' version '2.7.6'
	id 'io.spring.dependency-management' version '1.0.14.RELEASE'
	id 'java'
	id "org.openapi.generator" version "5.3.0"
}
~
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2'
    implementation 'org.openapitools:jackson-databind-nullable:0.2.1'
	compileOnly 'io.swagger:swagger-annotations:1.6.5'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
~~~

## 3.参考にしたリンク
- [JSON Schema](https://azisava.sakura.ne.jp/programming/0015.html)

APIのリクエスト/レスポンスを定義する際、JSONSchemaを利用することがあるが、その時に参考にしたサイト。
- [JSON Schemaのすゝめ](https://qiita.com/g0e/items/9a4f886897fd46f107a8)

JSONSchmeで参考にしたサイト、パート２

- [【第三回】Stream APIを使う前にラムダ式とメソッド参照を理解する](https://qiita.com/tech_newbie/items/2588970f3d2ce08a1b16)

コードの中にStreamAPI(for文の代わりに良く使われるらしい)が使われており、その説明が記載されてる。
ラムダ式((引数) -> 処理)といった書き方が最初わからなかったが、このサイトを参考にすれば、イメージは掴めそう

- [4．MapでStream APIを利用する方法](https://camp.trainocate.co.jp/magazine/java-map/)

StreamAPIで参考になりそうなサイト、パート２

- その他

staticメソッドプレゼンテーション(@Controllerのところ)でHTTPレスポンスを返す際にreturnでnewしてないクラスのメソッドを返していた。
これはClass内のメソッドがStaticメソッドであるから。
Staticメソッドはnewせずに使用可能であるが、Static自体、Classの特立性が失われるので利用ケースは考えた方が良いらしい。
詳しくは、「すっきり分かるjava入門」の14.3静的メンバを参照。



以上