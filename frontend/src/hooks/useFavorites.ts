import { useEffect, useState } from 'react'

const KEY = 'favorites'

export function useFavorites() {

  const [ids, setIds] = useState<number[]>(() => {
    try {
      const raw = localStorage.getItem(KEY);
      return raw ? JSON.parse(raw) : [];
    } catch {
      return [];
    }
  });

  useEffect(() => {
    try {
      localStorage.setItem(KEY, JSON.stringify(ids))
    } catch (e) { 
        console.error((e as Error).message); 
    }
  }, [ids])

  const toggle = (id: number) => setIds(prev => (prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]))

  const has = (id: number) => ids.includes(id)

  return { ids, has, toggle }
}