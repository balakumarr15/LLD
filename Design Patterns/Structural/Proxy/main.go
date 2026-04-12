package main

import "fmt"

type Server interface {
	handleRequest(url string) string
}

type Application struct {
}

func (app *Application) handleRequest(url string) string {
	return url + " - Success"
}

type Proxy struct {
	app          Server
	ratelimitMap map[string]int
}

func (proxy *Proxy) handleRequest(url string) string {
	if ratelimit, ok := proxy.ratelimitMap[url]; ok {
		proxy.ratelimitMap[url]++
		if ratelimit >= 1 && ratelimit < 2 {
			return proxy.app.handleRequest(url)
		} else {
			panic("rate limit exceeded")
		}
	} else {
		proxy.ratelimitMap[url] = 1
	}

	return proxy.app.handleRequest(url)
}

func main() {
	app := Application{}
	proxy := Proxy{app: &app, ratelimitMap: make(map[string]int)}

	fmt.Println(proxy.handleRequest("http://www.baidu.com"))
	fmt.Println(proxy.handleRequest("http://www.baidu.com"))
	fmt.Println(proxy.handleRequest("http://www.baidu.com"))
	fmt.Println(proxy.handleRequest("http://www.baidu.com"))
	// TODO make it proper using antigravity before pushing it to git
}
