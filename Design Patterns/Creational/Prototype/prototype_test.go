package prototype

import "testing"

func TestUserClone(t *testing.T) {
	original := &User{
		UserID:      1,
		Username:    "johndoe",
		Email:       "john@example.com",
		DisplayName: "John Doe",
		Age:         25,
	}

	cloned := original.Clone().(*User)

	// Verify all fields are copied
	if cloned.UserID != original.UserID ||
		cloned.Username != original.Username ||
		cloned.Email != original.Email ||
		cloned.DisplayName != original.DisplayName ||
		cloned.Age != original.Age {
		t.Error("cloned user should have the same attribute values as the original")
	}

	// Verify it's a different object in memory
	if original == cloned {
		t.Error("cloned user should not be the same pointer as the original")
	}
}

func TestUserCloneIsIndependent(t *testing.T) {
	original := &User{UserID: 1, Username: "alice", Email: "alice@example.com", DisplayName: "Alice", Age: 30}
	cloned := original.Clone().(*User)

	// Mutating the clone should not affect the original
	cloned.Username = "bob"
	cloned.Age = 40

	if original.Username == cloned.Username {
		t.Error("modifying the clone should not affect the original")
	}
	if original.Age == cloned.Age {
		t.Error("modifying the clone's age should not affect the original")
	}
}

func TestRegistryAddAndClone(t *testing.T) {
	registry := NewUserPrototypeRegistry()

	admin := &User{UserID: 100, Username: "admin", Email: "admin@platform.com", DisplayName: "Admin User", Age: 35}
	member := &User{UserID: 200, Username: "member", Email: "member@platform.com", DisplayName: "Regular Member", Age: 22}

	registry.AddPrototype("admin", admin)
	registry.AddPrototype("member", member)

	// Clone an admin user
	clonedAdmin := registry.Clone("admin")
	if clonedAdmin == nil {
		t.Fatal("cloned admin should not be nil")
	}
	if clonedAdmin == admin {
		t.Error("cloned admin should be a different object than the prototype")
	}
	if clonedAdmin.Username != "admin" || clonedAdmin.Age != 35 {
		t.Error("cloned admin should have the same attributes as the prototype")
	}

	// Clone a member user
	clonedMember := registry.Clone("member")
	if clonedMember == nil {
		t.Fatal("cloned member should not be nil")
	}
	if clonedMember.Email != "member@platform.com" {
		t.Error("cloned member should have the same email as the prototype")
	}
}

func TestRegistryGetPrototype(t *testing.T) {
	registry := NewUserPrototypeRegistry()

	user := &User{UserID: 1, Username: "test", Email: "test@example.com", DisplayName: "Test", Age: 20}
	registry.AddPrototype("test", user)

	retrieved := registry.GetPrototype("test")
	if retrieved != user {
		t.Error("GetPrototype should return the original prototype pointer")
	}
}

func TestRegistryCloneNonExistent(t *testing.T) {
	registry := NewUserPrototypeRegistry()

	result := registry.Clone("nonexistent")
	if result != nil {
		t.Error("cloning a non-existent type should return nil")
	}
}

func TestRegistryCloneIndependence(t *testing.T) {
	registry := NewUserPrototypeRegistry()
	registry.AddPrototype("default", &User{UserID: 1, Username: "default", Email: "default@example.com", DisplayName: "Default", Age: 25})

	clone1 := registry.Clone("default")
	clone2 := registry.Clone("default")

	// Mutate clone1
	clone1.Username = "modified"
	clone1.Age = 99

	// clone2 and the original prototype should be unaffected
	if clone2.Username != "default" || clone2.Age != 25 {
		t.Error("clones should be independent of each other")
	}

	original := registry.GetPrototype("default")
	if original.Username != "default" || original.Age != 25 {
		t.Error("cloning should not modify the original prototype")
	}
}
