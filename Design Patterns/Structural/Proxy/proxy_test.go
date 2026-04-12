package proxy

import "testing"

func TestApplicationHandleRequest(t *testing.T) {
	app := &Application{}
	result, err := app.HandleRequest("http://example.com")
	if err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
	if result != "http://example.com - Success" {
		t.Errorf("expected 'http://example.com - Success', got '%s'", result)
	}
}

func TestProxyAllowsRequestsWithinLimit(t *testing.T) {
	app := &Application{}
	p := NewProxy(app, 3)

	for i := 1; i <= 3; i++ {
		result, err := p.HandleRequest("http://example.com")
		if err != nil {
			t.Fatalf("request %d should succeed, got error: %v", i, err)
		}
		if result != "http://example.com - Success" {
			t.Errorf("request %d: expected success response, got '%s'", i, result)
		}
	}
}

func TestProxyBlocksAfterRateLimit(t *testing.T) {
	app := &Application{}
	p := NewProxy(app, 2)

	// First two requests should succeed
	for i := 1; i <= 2; i++ {
		_, err := p.HandleRequest("http://example.com")
		if err != nil {
			t.Fatalf("request %d should succeed, got error: %v", i, err)
		}
	}

	// Third request should be rate limited
	_, err := p.HandleRequest("http://example.com")
	if err == nil {
		t.Error("expected rate limit error on third request, got nil")
	}
}

func TestProxyRateLimitIsPerURL(t *testing.T) {
	app := &Application{}
	p := NewProxy(app, 1)

	_, err := p.HandleRequest("http://example.com")
	if err != nil {
		t.Fatalf("first URL should succeed: %v", err)
	}

	// Different URL should have its own limit
	_, err = p.HandleRequest("http://other.com")
	if err != nil {
		t.Fatalf("second URL should succeed: %v", err)
	}

	// First URL should now be rate limited
	_, err = p.HandleRequest("http://example.com")
	if err == nil {
		t.Error("expected rate limit error for first URL")
	}
}

func TestProxyImplementsServer(t *testing.T) {
	app := &Application{}
	var s Server = NewProxy(app, 5)
	_, err := s.HandleRequest("http://example.com")
	if err != nil {
		t.Fatalf("proxy as Server should work: %v", err)
	}
}
