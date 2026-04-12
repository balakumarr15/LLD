package proxy

import "fmt"

// Server defines the interface for handling requests.
type Server interface {
	HandleRequest(url string) (string, error)
}

// Application is the real server that handles requests.
type Application struct{}

func (app *Application) HandleRequest(url string) (string, error) {
	return url + " - Success", nil
}

// Proxy wraps a Server and enforces a per-URL rate limit.
type Proxy struct {
	app          Server
	maxRequests  int
	rateLimitMap map[string]int
}

// NewProxy creates a proxy with the given rate limit per URL.
func NewProxy(app Server, maxRequests int) *Proxy {
	return &Proxy{
		app:          app,
		maxRequests:  maxRequests,
		rateLimitMap: make(map[string]int),
	}
}

func (p *Proxy) HandleRequest(url string) (string, error) {
	p.rateLimitMap[url]++
	if p.rateLimitMap[url] > p.maxRequests {
		return "", fmt.Errorf("rate limit exceeded for %s", url)
	}
	return p.app.HandleRequest(url)
}
