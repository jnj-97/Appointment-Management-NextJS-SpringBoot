"use server";
// src/app/api/hello/route.ts

import { NextRequest, NextResponse } from "next/server";

export async function GET(request: NextRequest) {
  try {
    // Replace with your backend API URL
    const apiUrl = `${process.env.BACKEND_URL}data`;
    const response = await fetch(apiUrl);

    if (!response.ok) {
      return NextResponse.error();
    }

    const data = await response.json();

    return data;
  } catch (error) {
    console.error("Error fetching data:", error);
    return NextResponse.error();
  }
}

export async function POST(
  request: NextRequest,
  username: string,
  password: string
) {
  try {
    const requestData = await request.json();
    // Replace with your backend API URL
    const apiUrl = `${process.env.BACKEND_URL}login`;
    const response = await fetch(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ userName: username, password }),
    });
    if (!response.ok) {
      return NextResponse.error();
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching data:", error);
    return NextResponse.error();
  }
}
