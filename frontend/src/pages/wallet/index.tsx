import Link from "next/link";
import React, { useEffect, useState } from "react";
import { UserType } from "../../types/user";

const index = () => {
  const [uid, setUid] = useState<string>("");
  const [userList, setUserList] = useState<UserType[]>([]);

  const handleCreateUser = () => {
    const updateList = [
      ...userList,
      {
        uid: uid,
      },
    ];
    setUserList(updateList);

    localStorage.setItem("users", JSON.stringify(updateList));

    setUid("");
  };

  useEffect(() => {
    const users = localStorage.getItem("users");
    users && setUserList(JSON.parse(users));
  }, []);

  return (
    <div className="section">
      <div className="section my-4">
        <h2 className="section-title">ユーザーを作成する</h2>
        <div>
          <input
            type="text"
            className="border-[1px] border-black"
            value={uid}
            onChange={(e) => {
              setUid(e.target.value);
            }}
          />
          <button
            className="bg-blue-700 text-white  px-3 py-1 ml-2"
            onClick={() => {
              handleCreateUser();
            }}
          >
            作成
          </button>
        </div>
      </div>

      <div className="section">
        <h2 className="section-title">ユーザーリスト</h2>
        {userList.length ? (
          <>
            {userList?.map((user: UserType) => {
              return (
                <Link href={`/wallet/${user.uid}`}>
                  <a>{user.uid}</a>
                </Link>
              );
            })}
          </>
        ) : (
          <>ユーザーなし</>
        )}
      </div>
    </div>
  );
};

export default index;
