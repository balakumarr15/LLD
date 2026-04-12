package prototype

// ObjectClonable defines the interface for objects that can be cloned.
type ObjectClonable interface {
	Clone() ObjectClonable
}
