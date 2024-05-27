"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";

export default function Home() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();
  let [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    setError("");
    e.preventDefault();
    if (!!!username.length || !!!password.length) {
      setError("Username or Password cannot be empty!");
      return;
    }
    try {
      let data = await fetch("/api/register", {
        method: "POST",
        body: JSON.stringify({ username, password }),
      });
      if (!data.ok) {
        setError("Unknown Error occurred!");
      } else {
        let res = await data.json();
        console.log({ res });
        if (!!!res.status) {
          setError(res.response);
          console.log({ error });
        } else {
          router.push("/");
        }
      }
    } catch (err) {
      console.log(err);
    }
  }
  return (
    <div className="flex justify-center items-center mt-60">
      <div className="w-96 h-2/3 justify-center items-center bg-gradient-to-br p-16 from-cyan-100 to-amber-500 rounded-lg border-cyan-500 border-2">
        <form
          className="text-center"
          onSubmit={(e) => {
            handleSubmit(e);
          }}
        >
          <h1 className="text-4xl font-semibold mb-10 text-black">Register</h1>
          <label>Username</label>
          <input
            value={username}
            onChange={(e) => {
              setUsername(e.target.value);
            }}
            type="text"
            name="username"
            className="p-4 rounded-lg text-black"
          />
          <label>Password</label>
          <input
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            type="password"
            name="password"
            className="p-4 rounded-lg text-black"
          />
          <button
            type="submit"
            className="rounded-lg w-40 h-20 bg-gradient-to-tr from-green-200 to-green-600 mt-5"
          >
            Login
          </button>
        </form>
        <>
          {!!error.length && (
            <div className="text-center text-red-600 text-md">{error}</div>
          )}
        </>
        <div className="text-cyan-400 text-center text-lg">
          Already have an account? <a href="/register"> Log In</a>
        </div>
      </div>
    </div>
  );
}
