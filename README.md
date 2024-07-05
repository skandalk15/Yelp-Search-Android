# Yelp Search App - Android Project

This project is an Android application that allows users to search for restaurants and businesses, view detailed information about them, and make reservations. The application is designed to be user-friendly, following Google’s Material design guidelines, and leverages several third-party libraries for functionality and aesthetics.

## Table of Contents

1. [Demo](#demo)
2. [Features](#features)
3. [Installation](#installation)
4. [Usage](#usage)
5. [APIs](#apis)
6. [Libraries Used](#libraries-used)
7. [Implementation Details](#implementation-details)
8. [Contributing](#contributing)

## Demo

Watch the demo video below:

<iframe src="https://drive.google.com/file/d/1-5cyEnYmylkcg1vrSN3gHmeC2C46ITqS/preview" width="640" height="480" allow="autoplay"></iframe>

## Features

- **Home Screen**: 
  - Form with fields for keyword, distance, category, and location.
  - Autocomplete for keyword input using Yelp Autocomplete API.
  - Auto-detect location functionality.
  - Form validation with error handling.
  - Results section displaying business listings with tappable entries.
- **Search Functionality**:
  - Autocomplete suggestions for keywords.
  - Dynamic form validation and submission.
  - Search results display with business images, names, ratings, and distance.
- **Detailed Business Information**:
  - Three tabs: Business Details, Map Location, and Reviews.
  - Information includes address, price range, phone number, status, category, and URL.
  - Toolbar options for sharing to Facebook and Twitter.
- **Reservation System**:
  - Make reservations with date and time selection.
  - Validation for email and time.
  - View and manage reservations in the Reservations Activity.
  - Swipe to delete reservations.

## Installation

### Prerequisites

- Android Studio installed.
- Pixel 5 emulator with API 30 (recommended for consistency).

### Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/skandalk15/Yelp-Search-Android.git
   cd yelp-search-app
   ```

2. **Open the project in Android Studio**:
   - Import the project into Android Studio.
   - Sync the project with Gradle files.

3. **Run the app**:
   - Used the Pixel 5 emulator with API 30 to run the app.

## Usage

1. **Home Screen**:
   - Enter search criteria and submit the form.
   - View search results and tap on an entry for more details.
2. **Detailed Information**:
   - Navigate through tabs to view business details, map location, and reviews.
   - Use toolbar options to share information.
3. **Reservations**:
   - Make a reservation by providing email, date, and time.
   - View and manage existing reservations.

## APIs

- **Yelp Autocomplete API** for keyword suggestions.
- **Custom Node.js backend** for handling search queries and reservations.

## Libraries Used

- **Volley** for asynchronous HTTP requests.
- **Picasso** and **Glide** for image loading and caching.
- **RecyclerView** for displaying search results and reservations.

## Implementation Details

- **Splash Screen**: Implemented using a resource file for the launcher screen.
- **Form Validation**: Ensures all required fields are filled before submission.
- **Shared Preferences**: Used for storing reservation data locally.
- **Error Handling**: Displays appropriate messages for validation errors and API call failures.

## Contributing

Contributions are welcome! Please create a pull request or open an issue for suggestions or improvements.

### Steps to Contribute

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a pull request.

---
Thank you for using and contributing to the Yelp Search App! If you have any questions or need further assistance, feel free to open an issue or contact me at [sohamkan15@gmail.com].
