package prototype

// User represents a social media platform user.
type User struct {
	UserID      int
	Username    string
	Email       string
	DisplayName string
	Age         int
}

// Clone creates a deep copy of the User.
func (u *User) Clone() ObjectClonable {

	// Struct Copy
	newUser := *u
	return &newUser

	// return &User{
	// 	UserID:      u.UserID,
	// 	Username:    u.Username,
	// 	Email:       u.Email,
	// 	DisplayName: u.DisplayName,
	// 	Age:         u.Age,
	// }
}
