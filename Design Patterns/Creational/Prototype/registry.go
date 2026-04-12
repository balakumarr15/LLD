package prototype

// UserPrototypeRegistry defines methods for managing and cloning user prototypes.
type UserPrototypeRegistry interface {
	AddPrototype(userType string, prototype *User)
	GetPrototype(userType string) *User
	Clone(userType string) *User
}

// UserPrototypeRegistryImpl manages a collection of user prototypes.
type UserPrototypeRegistryImpl struct {
	prototypes map[string]*User
}

// NewUserPrototypeRegistry creates a new registry instance.
func NewUserPrototypeRegistry() UserPrototypeRegistry {
	return &UserPrototypeRegistryImpl{
		prototypes: make(map[string]*User),
	}
}

// AddPrototype registers a user prototype under the given type key.
func (r *UserPrototypeRegistryImpl) AddPrototype(userType string, prototype *User) {
	r.prototypes[userType] = prototype
}

// GetPrototype retrieves the original prototype for the given type.
func (r *UserPrototypeRegistryImpl) GetPrototype(userType string) *User {
	return r.prototypes[userType]
}

// Clone creates a cloned copy of the prototype registered under the given type.
func (r *UserPrototypeRegistryImpl) Clone(userType string) *User {
	prototype, exists := r.prototypes[userType]
	if !exists {
		return nil
	}
	return prototype.Clone().(*User)
}
