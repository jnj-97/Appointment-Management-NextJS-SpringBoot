"use client";
import { useState } from "react";

export default function Home() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  return (
    <div className="flex justify-center items-center mt-60">
      <div className="w-96 h-2/3 justify-center items-center bg-gradient-to-br p-16 from-cyan-100 to-amber-500 rounded-lg border-cyan-500 border-2">
        <form className="text-center">
          <h1 className="text-4xl font-semibold mb-10 text-black">Login</h1>
          <label>Username</label>
          <input
            value={username}
            onChange={(e) => {
              setUsername(e.target.value);
            }}
            type="text"
            name="username"
            className="p-4 rounded-lg"
          />
          <label>Password</label>
          <input
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            type="password"
            name="password"
            className="p-4 rounded-lg"
          />
          <button
            type="submit"
            className="rounded-lg w-40 h-20 bg-gradient-to-tr from-green-200 to-green-600 mt-5"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
}
