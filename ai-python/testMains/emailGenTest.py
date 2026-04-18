from services.email_service import generate_shift_email


def main():
    shifts = [
        {
            "day": "Friday 24th",
            "locations": [
                {"name": "Balboa Yacht Club", "priority": 1},
                {"name": "Irvine Location", "priority": 2}
            ]
        },
        {
            "day": "Saturday 25th",
            "locations": [
                {"name": "Concordia West Irvine", "priority": 1},
                {"name": "Balboa Yacht Club", "priority": 2},
            ]
        }
    ]

    result = generate_shift_email(
        name="Erfan Tavassoli",
        shifts=shifts
    )

    print("\n===== GENERATED EMAIL =====\n")
    print(result)


if __name__ == "__main__":
    main()